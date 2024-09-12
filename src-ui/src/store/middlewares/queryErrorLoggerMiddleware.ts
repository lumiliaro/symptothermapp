import { notifications } from "@mantine/notifications";
import type { Middleware, MiddlewareAPI } from "@reduxjs/toolkit";
import { isRejectedWithValue } from "@reduxjs/toolkit";
import { ErrorDto } from "../api/generatedApi";

/**
 * Log a warning and show a toast!
 */
export const queryErrorLoggerMiddleware: Middleware =
    (_api: MiddlewareAPI) => (next) => (action: any) => {
        if (isRejectedWithValue(action)) {
            const errorData = action.payload?.data as ErrorDto;
            const notificationId =
                action.meta?.arg?.originalArgs?.notificationId;

            if (notificationId) {
                notifications.update({
                    id: notificationId,
                    loading: false,
                    autoClose: 4000,
                    title: errorData?.code || "Fehler bei der Anfrage",
                    message:
                        errorData?.message ||
                        "Fehlermeldung konnte nicht ermittelt werden.",
                    color: "red",
                });
            } else {
                notifications.show({
                    title: errorData?.code || "Fehler bei der Anfrage",
                    message:
                        errorData?.message ||
                        "Fehlermeldung konnte nicht ermittelt werden.",
                    color: "red",
                    autoClose: 4000,
                    withCloseButton: true,
                });
            }

            console.error("API-Fehler:", errorData);
        }

        return next(action);
    };
