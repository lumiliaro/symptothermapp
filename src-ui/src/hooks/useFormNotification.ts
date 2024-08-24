import { NotificationData, notifications } from "@mantine/notifications";

export default function useFormNotification() {
    const openLoadingNotification = (notification: NotificationData) => {
        return notifications.show({
            title: "Laden...",
            ...notification,
            loading: true,
            autoClose: false,
            withCloseButton: false,
        });
    };

    const updateNotificationToSuccess = (
        id: string,
        notification: NotificationData
    ) => {
        if (id) {
            notifications.update({
                id,
                loading: false,
                ...notification,
                autoClose: 2000,
                color: "teal",
                title: "Erfolgreich",
            });
        }
    };

    const updateNotificationToFailure = (
        id: string,
        notification: NotificationData
    ) => {
        if (id) {
            notifications.update({
                id,
                loading: false,
                ...notification,
                autoClose: 4000,
                color: "red",
                title: "Fehlgeschlagen",
            });
        }
    };

    return {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    };
}
