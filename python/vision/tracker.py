# !/usr/bin/env python

from imutils.video import VideoStream

import numpy as np
import cv2
import time
import imutils

def main():

    # the lower HSV range to threshold
    lower_range = (10, 100, 100)
    # the upper HSV range to threshold
    upper_range = (25, 255, 255)

    # centimetres
    object_radius = 4
    # guess, likely not accurate
    focal_length = 300
    # this font is used to write the distance to the frame
    font = cv2.FONT_HERSHEY_PLAIN
    
    # start a video stream on the first video source
    vs = VideoStream(src=0).start()
    
    # wait for the camera to start recording 
    time.sleep(2.0)

    while True:
        frame = vs.read()

        frame = imutils.resize(frame, width=600)

        if frame is None:
            break

        # lab = cv2.cvtColor(frame, cv2.COLOR_BGR2LAB)
        # labPlanes = cv2.split(lab)
        # clahe = cv2.createCLAHE(clipLimit=1, tileGridSize=(8, 8))
        # labPlanes[0] = clahe.apply(labPlanes[0])
        # lab = cv2.merge(labPlanes)

        # frame = cv2.cvtColor(lab, cv2.COLOR_LAB2BGR)

        # blur the frame to denoise the image
        blurred = cv2.GaussianBlur(frame, (5, 5), 0)
        # convert to the HSV colour space
        hsv = cv2.cvtColor(blurred, cv2.COLOR_BGR2HSV)

        # threshold based on the low and high HSV range to threshold
        mask = cv2.inRange(hsv, lower_range, upper_range)
        # erode then dilate to remove noise
        mask = cv2.erode(mask, None, iterations=3)
        mask = cv2.dilate(mask, None, iterations=3)

        #transformed = cv2.distanceTransform(mask, cv2.DIST_L2, 3)
        #mask = np.uint8(cv2.normalize(transformed, 0, 255, cv2.NORM_MINMAX))

        # find the contours in the mask
        contours = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        contours = imutils.grab_contours(contours)
        centre = None

        if len(contours) > 0:
            # get the most optimal contour based on size and solidity
            largest_contour = max(contours, key=lambda c: cv2.contourArea(c) * safe_divide(cv2.contourArea(c),
            cv2.contourArea(cv2.convexHull(c))) ** 2)

            ((x, y), radius) = cv2.minEnclosingCircle(largest_contour)
            
            # get the contour moments and calculate the centre of the circle
            moments = cv2.moments(largest_contour)
            try:
                centre = (int(moments["m10"] / moments["m00"]), int(moments["m01"] / moments["m00"]))
            except ZeroDivisionError:
                print("Division by zero: ignoring frame")

            # minimum radius of circle
            if radius > 10:
                # draw the circles alongside with their centres
                cv2.circle(frame, (int(x), int(y)), int(radius), (0, 255, 255), 2)
                cv2.circle(frame, centre, 5, (0, 0, 255), -1)
                
                # calculate the distance based on focal length
                distance = ((object_radius * focal_length) / radius)
                cv2.putText(frame, "{0:.2f}".format(distance) + ' cm', (30, 30), font, 2, (0, 0, 255), 2, cv2.LINE_AA)

        cv2.imshow("mask", mask)
        cv2.imshow("hsv", hsv[:,:,0])
        cv2.imshow("frame", frame)
         
        key = cv2.waitKey(1) & 0xFF

        if key == ord("q"):
            break

    vs.stop()
    cv2.destroyAllWindows()

def safe_divide(n, d):
    return n / d if d else 0

if __name__ == "__main__":
    main()
