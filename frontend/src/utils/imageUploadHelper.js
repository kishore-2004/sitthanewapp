import * as ImagePicker from 'expo-image-picker';
import { Alert, Platform } from 'react-native';

/**
 * Request camera and media library permissions
 */
export const requestImagePermissions = async () => {
  try {
    const { status: cameraStatus } = await ImagePicker.requestCameraPermissionsAsync();
    const { status: mediaStatus } = await ImagePicker.requestMediaLibraryPermissionsAsync();
    
    if (cameraStatus !== 'granted' || mediaStatus !== 'granted') {
      Alert.alert(
        'Permission Required',
        'We need permission to access your camera and photo library to upload images.',
        [{ text: 'OK' }]
      );
      return false;
    }
    return true;
  } catch (error) {
    console.error('Permission error:', error);
    return false;
  }
};

/**
 * Pick image from gallery
 */
export const pickImageFromGallery = async () => {
  const hasPermission = await requestImagePermissions();
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
  const hasPermission = await requestImagePermissions();
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
