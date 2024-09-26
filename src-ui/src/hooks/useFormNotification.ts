import { NotificationData, notifications } from "@mantine/notifications";
import { unwrapResult } from "@reduxjs/toolkit";
import { ReactNode } from "react";
import { UseFormReturn } from "react-hook-form";
import handleFormErrors from "../utils/FormErrorHandler.utils";

export default function useFormNotification() {
    const openLoadingNotification = (notification: NotificationData) => {
        return notifications.show({
            title: "Anfrage wird ausgeführt. Bitte warten...",
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

    const callApiWithNotification = async (props: {
        apiCall: (notificationId: string) => Promise<any>;
        form: UseFormReturn<any>;
        initNotificationMessage?: ReactNode;
        successNotificationMessage?: ReactNode;
    }) => {
        const {
            apiCall,
            form,
            initNotificationMessage,
            successNotificationMessage,
        } = props;
        const notificationId = openLoadingNotification({
            message: initNotificationMessage,
        });

        try {
            const resultAction = await apiCall(notificationId);
            const result = unwrapResult(resultAction);

            updateNotificationToSuccess(notificationId, {
                message: successNotificationMessage,
            });

            return result;
        } catch (error) {
            handleFormErrors(form, error);
        }
    };

    return {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
        callApiWithNotification,
    };
}
