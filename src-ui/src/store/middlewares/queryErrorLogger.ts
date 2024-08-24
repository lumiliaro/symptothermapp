import { notifications } from "@mantine/notifications";
import type { Middleware, MiddlewareAPI } from "@reduxjs/toolkit";
import { isRejectedWithValue } from "@reduxjs/toolkit";

/**
 * Log a warning and show a toast!
 */
export const queryErrorLogger: Middleware =
    (_api: MiddlewareAPI) => (next) => (action: any) => {
        if (isRejectedWithValue(action)) {
            notifications.show({
                title: action.payload?.data?.error || "Fehler bei der Anfrage",
                message:
                    action.payload?.data?.message ||
                    "Fehlermeldung konnte nicht ermittelt werden.",
                color: "red",
                autoClose: 4000,
                withCloseButton: true,
            });
        }

        return next(action);
    };
