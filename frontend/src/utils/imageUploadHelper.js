import * as ImagePicker from 'expo-image-picker';
import { Alert, Platform, Linking } from 'react-native';

/**
 * Request media library permission for gallery access
 */
export const requestMediaLibraryPermission = async () => {
  try {
    const mediaPermission = await ImagePicker.getMediaLibraryPermissionsAsync();
    let mediaStatus = mediaPermission.status;

    if (mediaStatus !== 'granted') {
      const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
      mediaStatus = status;
    }

    if (mediaStatus !== 'granted') {
      Alert.alert(
        'Permission Required',
        'Please enable photo library permissions in your device settings to upload images.',
        [
          { text: 'Cancel', style: 'cancel' },
          { text: 'Open Settings', onPress: () => Linking.openSettings() }
        ]
      );
      return false;
    }
    return true;
  } catch (error) {
    console.error('Media library permission error:', error);
    Alert.alert('Error', 'Failed to request media library permission. Please try again.');
    return false;
  }
};

/**
 * Request camera permission for camera access
 */
export const requestCameraPermission = async () => {
  try {
    const cameraPermission = await ImagePicker.getCameraPermissionsAsync();
    let cameraStatus = cameraPermission.status;

    if (cameraStatus !== 'granted') {
      const { status } = await ImagePicker.requestCameraPermissionsAsync();
      cameraStatus = status;
    }

    if (cameraStatus !== 'granted') {
      Alert.alert(
        'Permission Required',
        'Please enable camera permissions in your device settings to take photos.',
        [
          { text: 'Cancel', style: 'cancel' },
          { text: 'Open Settings', onPress: () => Linking.openSettings() }
        ]
      );
      return false;
    }
    return true;
  } catch (error) {
    console.error('Camera permission error:', error);
    Alert.alert('Error', 'Failed to request camera permission. Please try again.');
    return false;
  }
};

/**
 * Pick image from gallery
 */
export const pickImageFromGallery = async () => {
  const hasPermission = await requestMediaLibraryPermission();
  if (!hasPermission) return null;

  try {
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 0.8,
    });

    if (!result.canceled && result.assets && result.assets.length > 0) {
      return result.assets[0];
    }
    return null;
  } catch (error) {
    Alert.alert('Error', 'Failed to pick image. Please try again.');
    console.error('Image picker error:', error);
    return null;
  }
};

/**
 * Take photo with camera
 */
export const takePhotoWithCamera = async () => {
  const hasPermission = await requestCameraPermission();
  if (!hasPermission) return null;

  try {
    const result = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [1, 1],
      quality: 0.8,
    });

    if (!result.canceled && result.assets && result.assets.length > 0) {
      return result.assets[0];
    }
    return null;
  } catch (error) {
    Alert.alert('Error', 'Failed to take photo. Please try again.');
    console.error('Camera error:', error);
    return null;
  }
};

/**
 * Show image source selection
 */
export const showImageSourceOptions = () => {
  return new Promise((resolve) => {
    Alert.alert(
      'Upload Image',
      'Choose an option',
      [
        {
          text: 'Take Photo',
          onPress: async () => {
            const image = await takePhotoWithCamera();
            resolve(image);
          },
        },
        {
          text: 'Choose from Gallery',
          onPress: async () => {
            const image = await pickImageFromGallery();
            resolve(image);
          },
        },
        {
          text: 'Cancel',
          style: 'cancel',
          onPress: () => resolve(null),
        },
      ],
      { cancelable: true }
    );
  });
};
