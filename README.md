# Face_Recognition
## Description
1. A prototype system of face recognition access control system based on PCA algorithm is designed and implemented using OpenCV, an open source cross-platform computer vision library. 
2. Aiming at the low real-time rate of face detection using Haar classifier and the vulnerability of PCA face recognition algorithm to environmental impact, an image processing method combining image reduction, image graying, histogram equalization and Gauss bilateral blur processing technology is proposed. It effectively solves the problem of face detection video picture carton and ambient illumination factors which lead to unclear face picture and many noise points.
3. Experiments show that the proposed method is effective, feasible and correct in improving the efficiency of face detection and recognition algorithms.

## Main purposes
1. Call an external USB camera to obtain real-time video frame images;
2. Load a face classifier based on Haar features to detect faces in video frames;
3. Collect and preprocess the face image of the detected face;
4. Principal component analysis (PCA) algorithm is used to extract facial features through dimensionality reduction;
5. Calculate the Euclidean distance of the comparison face image matrix for face matching;
6. Load a human eye classifier based on Haar features to recognize blinks and realize live detection;
7. According to the results of face matching and living body detection, call the database to complete identity verification;
8. Realize the input and update of the face database through the collection and storage of face images;
9. Realize the query management of access control data, including blacklist management, visitor query, etc.;
10. Improve the preprocessing method of face images by using image processing technology to improve the real-time performance of face detection using the Haar classifier, and reduce the impact of unclear face images and excessive noise caused by ambient light interference. Improve the recognition success rate of PCA face recognition algorithm;
11. Experimentally test the improved method, verify the results, and draw conclusions.

## Related technologies
### OpenCV
OpenCV is a cross-platform open source computer vision library that contains many image processing algorithms. OpenCV is based on C/C++ programming, and its advantages are mainly reflected in its independence from operating system, cross-platform, image and hardware management, etc. It is free to use OpenCV in commercial or non-commercial fields, and is easy to use and fast with strong scalability.
### Haar feature classifier
Haar feature, also known as Haar-like feature or rectangular feature, is used to describe the image features of target detection. It was first applied to the representation of facial features by Papageorgiou et al., and later Viola and Jones expanded on this basis, proposed various forms of Haar features to better describe the gray distribution of the target image.<br/>
The Haar feature model consists of 2 to 3 black and white rectangles. Two rectangles are called two-rectangular features, which are used to describe image edge features, and three-rectangular features are used to describe linear features.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture1.png)<br/>
The facial features of a human face can be described using Haar features. The eye area is darker than other areas of the face, the nose area is darker than the cheeks, and the mouth area is darker than the surrounding skin. By using the Haar feature template to match the face features, the location of the face can be found in the image.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture1_1.png)
### PCA
PCA (Principal Component Analysis) is a widely used data dimensionality reduction algorithm. In data processing, due to the large amount of data, it is often necessary to reduce the dimensionality of the data first, but the main feature information is not expected to be lost in the dimensionality reduction process. The PCA algorithm is a dimensionality reduction algorithm that retains the main components of the data.<br/>

The PCA-based face algorithm is also called eigenface algorithm, which is a commonly used algorithm in face recognition. The purpose of the PCA face recognition algorithm is to reduce the dimensions of the face image, reduce the amount of calculation during face recognition, and improve the efficiency of the face recognition algorithm.
The specific process of the PCA-based face recognition algorithm is as follows:
1. Read the image in the face training set, and store the specified number of face image pixel values in a two-dimensional array X. Each column represents a face image, and the number of columns represents the number of faces. If there are S face images in the training set, and the length and width of each face image are H, W, then X=[HW,S];
2. Calculate the average value of each row, and subtract the pixel value of each row from the average value to obtain the difference between each face and the average face;
3. Calculate the covariance matrix Y of matrix X, the size is HW*HW;
4. Calculate the eigenvalues and eigenvectors of Y, HW eigenvalues correspond to HW eigenvectors;
5. Arrange all eigenvalues in descending order, and select the first P eigenvectors as principal component eigenvectors according to the accuracy requirements, and P is much smaller than HW;
6. Form a matrix [P,HW] by the principal component eigenvectors, and multiply the matrix with the original matrix [HW,S] to obtain the reduced-dimensional matrix [P,S];
7. Use the same method to reduce the dimensionality of the test set. If the test set has Q images, the matrix after dimensionality reduction is [P,Q];
8. Use Euclidean distance to find the closest face image of the test set dimensionality reduction matrix [P, Q] and the training set dimensionality reduction matrix [P, S] to complete face recognition.

## System feasibility analysis
This system uses Eclipse Java as the development environment, MySQL as the backend database, and Swing as the user interface. The image processing uses the cross-platform open source computer vision library OpenCV 3.4.1, and the face recognition algorithm uses the PCA face recognition algorithm. The OpenCV vision library is very mature and the development documentation is complete. The PCA face recognition algorithm is a classic face recognition algorithm with numerous related documents and literature. Therefore, there are no unknown problems technically.

## System characteristics and indicators
This system uses face recognition as the basis of access control identity verification, so it improves the real-time performance of face detection and the recognition success rate of face recognition algorithm is a focus of this article. This article simulates the actual use scene, uses the camera to read the video in real time, and can prevent the use of photos for fraud by blinking live detection. This system designs a management center for managers and provides a platform for related users to query and manage access control data to facilitate access control management. Combining the above characteristics, the performance indicators of the system are as follows:
1. Face recognition video stream frames: 30fps;
2. Face recognition video stream size: 360*240;
3. Live body detection method: blink detection;
4. Face recognition time limit: 6s.

## System functions
The system consists of five functional modules: face recognition module, face input module, visitor record module, user information management module and face database management module. It realizes face image acquisition, face detection, face image pre-processing, face recognition and biopsy detection of camera video pictures, and provides related functions of access control data query management for managers. 
### Face recognition module
In the face recognition module, the system calls an external USB camera to obtain real-time video images, completes face detection, image preprocessing, face recognition, and blinking living body detection for visitors, and can give identity verification results based on the detection results, such as identity verification Success, authentication failure, or no access rights.
### Face input module
In the face input module, managers can add faces for employees in the local face database. You can choose to add from the camera and from the picture in two ways. If it is added from a camera, the system calls an external USB camera to obtain real-time video images, completes face detection for visitors, and saves the face image to the local face library; if it is added from a picture, the manager manually selects a picture containing a face to upload , The system automatically detects and displays the face in the picture, and the manager selects the face to be entered and saves it to the local face database.
### Visitor record module
In the visitor record module, the administrator can query visitor records based on keywords and time periods. The query results include the visitor's identity information, visiting time, face pictures, and operations performed.
### User Information Management Module
In the user information management module, managers can query, add or delete users. After deleting a user, all face pictures of the user in the local face library will be automatically cleared. Administrators can also set up a blacklist for users, and the users in the blacklist will lose the access control authority.
### Face database management module
In the face database management module, managers can visually manage all faces in the local face database, query faces by keywords or face collection time, and can manually delete useless faces, such as time Face images that are too long or of low quality.

## Data dictionary
### Admin form
| Field name | Primary key | Type | Length | Allow empty | Field description |
| --- | --- | --- | --- | --- | --- |
| id | √ | int | 10 | | Administrator id |
| username | | nvarchar | 10 | √ | Administrator username |
| password | | nvarchar | 10 | √ | Administrator password |

### Employee form
| Field name | Primary key | Type | Length | Allow empty | Field description |
| --- | --- | --- | --- | --- | --- |
| emworkid | √ | int | 10 | | Employee id |
| emname | | nvarchar | 10 | √ | Employee name |
| emdept | | nvarchar | 5 | √ | Employee department |
| emfaceid | | nvarchar | 10 | √ | Employee face database id |
| emlimit | | nvarchar | 5 | √ | Employee permissions |

### Face form
| Field name | Primary key | Type | Length | Allow empty | Field description |
| --- | --- | --- | --- | --- | --- |
| faceno | √ | int | 10 | | Face ID |
| emworkid | | nvarchar | 10 | √ | Employee ID |
| facedate | | datetime | 23 | √ | Face collection date |
| facepath | | nvarchar | 50 | √ | Face image path |

### Record form
| Field name | Primary key | Type | Length | Allow empty | Field description |
| --- | --- | --- | --- | --- | --- |
| rdid | √ | int | 10 | | Record ID |
| emworkid | | nvarchar | 10 | √ | Employee ID |
| rddate | | datetime | 23 | √ | Visit date |
| rdpath | | nvarchar | 50 | √ | Face record path |
| rdaction | | nvarchar | 5 | √ | Operation |

### Dept form
| Field name | Primary key | Type | Length | Allow empty | Field description |
| --- | --- | --- | --- | --- | --- |
| dpid | √ | int | 5 | | Department ID |
| dpname | | nvarchar | 10 | √ | Department name |

## Implementation
### Face recognition module
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture2.png)<br/>
In the face recognition module, first call the `OpenCVFrameGrabber` class to obtain the video frame captured by the camera in real time, and then call the `CascadeClassifier` class to load the Haar feature-based face classifier to detect the face of the video frame, and frame the face in the picture location.
The core code for face detection is as follows:
```
OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);//Initialize the frame grabber, parameter 1 is to call the external USE camera.
grabber.start();//Start to get the camera picture
grabber.grab();//Capture the camera picture
CascadeClassifier faceDetector = new CascadeClassifier();//Initialize the CascadeClassifier class
faceDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_frontalface_default.xml");//Load a face classifier based on Haar features
faceDetector.detectMultiScale(mat, faces);//Use a classifier to detect faces;
```
In order to improve the efficiency of face recognition, face images need to be preprocessed. The OpenCV image preprocessing methods selected by this system are grayscale, histogram equalization, and Gaussian bilateral blur. The functions of the above operations are to reduce color information, Enhance pictures and remove noise.<br/>
The core code of image preprocessing is as follows:
```
resize(mat, mat1, new Size(320,240), 0, 0, INTER_LINEAR);//image reduction
cvtColor(mat1, mat2, COLOR_RGB2GRAY);//Grayscale
equalizeHist(mat2, mat3);//Histogram equalization
bilateralFilter(mat3,mat4,0,8,8);//Gaussian bilateral blur, the last three parameters are the diameter of each pixel neighborhood used, the sigma parameter of the color space and the sigma parameter of the coordinate space
```
After the image preprocessing is completed, the PCA-based face recognition algorithm is used to realize identity verification by calculating the Euclidean distance.<br/>
The core code of face recognition is as follows:
```
/**De-averaging**/
for (int i = 0; i <trainNumber; i++) {
Mat colRange = matVector.col(i);
Core.subtract(colRange, horizontalMean, tempMat);
listMatVec.add(tempMat);
}
//Merge by column
Core.hconcat(listMatVec, imgVecCenter);
eigenFaceCore.setImgVecCenter(imgVecCenter);//Image vector center
/**L=A'*A**/
Mat tempMutipil=new Mat();
Core.gemm(imgVecCenter.t(), imgVecCenter, 1, new Mat(), 0, tempMutipil);
Mat eigenValues=new Mat();//L eigenvalues
Mat eigenVectors=new Mat();//L feature vector
/**Find the eigenvalues and eigenvectors of L**/
Core.eigen(tempMutipil, eigenValues, eigenVectors);
/**Calculate Euclidean distance**/
eucDist.create(trainNumber,1,CvType.CV_64FC1);
for (int i = 0; i <trainNumber; i++) {
trainProCol = projectedImages.col(i);
Core.subtract(projectedTestImage, trainProCol, temp);
distence = Math.pow(Core.norm(temp), 2);
eucDist.put(i, 0, distence);}
//Find the minimum
minDis = Core.minMaxLoc(eucDist).minVal;
minLoc = Core.minMaxLoc(eucDist).minLoc.y;
```
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture3.png)<br/>
In order to prevent photo fraud, visitors must pass the blink live detection, this operation will improve the security of the access control management system. Visitors should blink according to the voice prompts, complete the identity verification only after completing the specified number of blinks, and maintain the integrity of the face during the process. This system calls the Haar-based eye classifier to recognize human eyes.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture4.png)
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture5.png)
### Face input module
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture6.png)
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture7.png)<br/>
Click the face input button to enter the face input module. Managers can choose camera entry and or picture entry according to the actual situation. After clicking the picture entry, the manager can select a picture containing a face locally through the file selection box above, and the system will call the face classifier to automatically detect all the faces contained in the picture, and display it on the right. You need to select the face image that needs to be input.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture8.png)
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture9.png)
### Visitor record module
In the visitor record module, managers can query visitor records by visitor name, time period, and operations performed. The time selector used for time period input is the third-party time selection control `com.eltima.components.ui.DatePicker`, The control can provide time selection accurate to the second, and the interface is more beautiful.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture10.png)
### User information management module
In the user information management module, managers can inquire about all users through employee ID, department and name, and managers can move designated users into or out of the blacklist. You can add a new employee or delete an existing employee through the add and delete buttons. Adding a new employee will automatically create a face directory for the new employee in the face database, and deleting an employee will also clear all of face images in the employee’s face database.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture11.png)

### Face database management module
In the face database management module, managers can query and manage all face images in the face database in a visual manner. By entering the department, name, and collection date, managers can accurately search for facial images, and can delete facial images that are of poor image quality or that are too long.<br/>
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture12.png)
![](https://github.com/jameswyh/Face_Recognition/blob/master/face_readme_pic/Picture13.png)

## Real-time experimental results and analysis of face detection
The real-time performance of face detection is an important indicator of face recognition. Therefore, there is a relatively high requirement on the speed of face detection. If the detection speed is too slow, people will feel the stutter of the video picture. 
> When the human eye is watching a video stream, there will be no visual stuttering after more than 25 frames per second
### Experimental scheme
The experiment uses three different sizes of video streams to perform real-time face detection through the Haar classifier, and observe the efficiency of face detection in the case of different image sizes. The maximum video capture frame rate of the USB external camera used in this experiment is 30fps.
### Analysis of experimental results
| Input video stream size | Input video stream frame number | Face detection classifier | Output video stream frame number | 
| --- | --- | --- | --- |
| 640×480 | 30fps | haarcascade_frontalface_default | 14fps |
| 320×240 | 30fps | haarcascade_frontalface_default | 29fps |
| 160×120 | 30fps | haarcascade_frontalface_default | 30fps |

In the above experiment, when using the same input video frame number of 30fps and the same face classifier, the 640*480 video stream after face detection, the output video stream is only 14fps, and the human eye can feel the obvious card pause. When the input size is reduced to 320*240, the number of output frames after face detection reaches 29fps, at this time the human eye can no longer feel the existence of the stutter. When the input size is reduced to 160*120, the number of output frames reaches 30fps, which is consistent with the input resolution.<br/>
According to the analysis of the experimental results, reducing the size of the detected image can effectively reduce the amount of calculation during face detection, increase the speed of face detection, and improve the real-time performance of face detection.

## Experimental results and analysis of face recognition recognition rate
One of the major drawbacks of the PCA face recognition algorithm is that it has high requirements for the quality of the face image, so it is extremely susceptible to the influence of the external environment. If the light is too dark, it may cause the picture to be too dark and excessively noisy; if the light is too bright, it may cause problems such as uneven brightness of the face and the picture is too bright. These problems will greatly affect the feature extraction of face recognition. Therefore, it is necessary to preprocess the face image before face recognition. In view of the above-mentioned possible problems, the system adopts histogram equalization and Gaussian bilateral blur processing in addition to the traditional pre-processing method grayscale.
### Experimental scheme
The traditional image preprocessing method grayscale and the preprocessing method that combines grayscale, histogram equalization, and Gaussian bilateral blurring used in this system are used to compare the success rate of the PCA face recognition algorithm under different lighting environments. For analysis, the face images in the face training set used in the experiment are all collected under good lighting, and the Euclidean distance threshold used is 2*1015, that is, when the Euclidean distance is less than or equal to 2*1015, the recognition is judged to be successful , Otherwise the recognition fails.
### Analysis of experimental results
| Environment lighting conditions | Face recognition times | Face recognition success times | Face recognition success rate |
| --- | --- |  --- |  --- |
| Good light | 10 | 9 | 90% |
| Brighter light | 10 | 1 | 10% |
| Dim light | 10 | 0 | 0% |

In the above experiment, the PCA algorithm has a high recognition success rate of 90% only in a good lighting environment, and the recognition success rate is only 10% and 0% in a brighter and darker environment, which is almost unrecognizable.<br/>
According to the analysis of the experimental results, the PCA face recognition algorithm has more stringent requirements for environmental lighting, and brighter or darker lighting will greatly reduce the recognition success rate.<br/>

| Environment lighting conditions | Face recognition times | Face recognition success times | Face recognition success rate |
| --- | --- |  --- |  --- |
| Good light | 10 | 10 | 100% |
| Brighter light | 10 | 4 | 40% |
| Dim light | 10 | 6 | 60% |

In the above experiment, after the face image is preprocessed, the PCA algorithm has a recognition success rate of 100% in a good lighting environment, and the recognition success rate in a brighter and darker environment has increased to 40% and 60%.<br/>
According to the analysis of the experimental results, the face image can effectively reduce the impact of unclear face image and excessive noise caused by environmental factors after grayscale, histogram equalization and Gaussian bilateral blur preprocessing, to a certain extent The above improves the recognition success rate of the PCA-based face recognition algorithm in brighter and darker lighting environments.




