import { NotificationData, notifications } from "@mantine/notifications";

export default function useFormNotification() {
    // const [notificationId, setNotificationId] = useState<string>("");
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
        console.log("success", id);
        if (id) {
            notifications.update({
                id,
                loading: false,
                ...notification,
                autoClose: 2000,
                color: "teal",
                title: "Erfolgreich",
                // icon: (
                //     <IconCheck
                //         style={{ width: rem(18), height: rem(18) }}
                //     />
                // ),
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
                autoClose: 2000,
                color: "red",
                title: "Fehlgeschlagen",
                // icon: (
                //     <IconCheck
                //         style={{ width: rem(18), height: rem(18) }}
                //     />
                // ),
            });
        }
    };

    return {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    };
}
