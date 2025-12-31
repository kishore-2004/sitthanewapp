import * as ImagePicker from 'expo-image-picker';
import { Alert, Linking } from 'react-native';

/**
 * SIMPLIFIED VERSION - Request media library permission for gallery access
 * This follows the recommended React Native / Expo pattern
 */
export const requestMediaLibraryPermissionSimple = async () => {
    const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();

    if (status !== 'granted') {
        Alert.alert(
            'Permission Required',
            'Please allow gallery access to upload photos',
            [
                { text: 'Cancel', style: 'cancel' },
                { text: 'Open Settings', onPress: () => Linking.openSettings() }
            ]
        );
        return false;
    }
    return true;
};

/**
 * ADVANCED VERSION - Request media library permission with canAskAgain handling
 * This provides better UX for production apps
 */
export const requestMediaLibraryPermission = async () => {
    try {
        // First check current permission status
        const { status: currentStatus, canAskAgain } = await ImagePicker.getMediaLibraryPermissionsAsync();

        // If already granted, return true
        if (currentStatus === 'granted') {
            return true;
        }

        // If permission was denied and we can't ask again (user selected "Don't ask again")
        if (currentStatus === 'denied' && !canAskAgain) {
            Alert.alert(
                'Permission Required',
                'Photo library access was previously denied. Please enable it in your device settings to upload images.',
                [
                    { text: 'Cancel', style: 'cancel' },
                    { text: 'Open Settings', onPress: () => Linking.openSettings() }
                ]
            );
            return false;
        }

        // Request permission
        const { status: newStatus, canAskAgain: canAskAgainAfter } = await ImagePicker.requestMediaLibraryPermissionsAsync();

        if (newStatus === 'granted') {
            return true;
        }

        // Permission denied
        Alert.alert(
            'Permission Required',
            canAskAgainAfter
                ? 'Please allow photo library access to upload images.'
                : 'Please enable photo library permissions in your device settings to upload images.',
            [
                { text: 'Cancel', style: 'cancel' },
                { text: 'Open Settings', onPress: () => Linking.openSettings() }
            ]
        );
        return false;
    } catch (error) {
        console.error('Media library permission error:', error);
        Alert.alert('Error', 'Failed to request media library permission. Please try again.');
        return false;
    }
};

/**
 * Request camera permission
 */
export const requestCameraPermission = async () => {
    try {
        const { status: currentStatus, canAskAgain } = await ImagePicker.getCameraPermissionsAsync();

        if (currentStatus === 'granted') {
            return true;
        }

        if (currentStatus === 'denied' && !canAskAgain) {
            Alert.alert(
                'Permission Required',
                'Camera access was previously denied. Please enable it in your device settings to take photos.',
                [
                    { text: 'Cancel', style: 'cancel' },
                    { text: 'Open Settings', onPress: () => Linking.openSettings() }
                ]
            );
            return false;
        }

        const { status: newStatus, canAskAgain: canAskAgainAfter } = await ImagePicker.requestCameraPermissionsAsync();

        if (newStatus === 'granted') {
            return true;
        }

        Alert.alert(
            'Permission Required',
            canAskAgainAfter
                ? 'Please allow camera access to take photos.'
                : 'Please enable camera permissions in your device settings to take photos.',
            [
                { text: 'Cancel', style: 'cancel' },
                { text: 'Open Settings', onPress: () => Linking.openSettings() }
            ]
        );
        return false;
    } catch (error) {
        console.error('Camera permission error:', error);
        Alert.alert('Error', 'Failed to request camera permission. Please try again.');
        return false;
    }
};

/**
 * Pick image from gallery - RECOMMENDED USAGE
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
 * Take photo with camera - RECOMMENDED USAGE
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
