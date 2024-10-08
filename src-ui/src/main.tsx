import { createTheme, MantineProvider } from "@mantine/core";
import "@mantine/core/styles.css";
import { DatesProvider } from "@mantine/dates";
import "@mantine/dates/styles.css";
import { ModalsProvider } from "@mantine/modals";
import { Notifications } from "@mantine/notifications";
import "@mantine/notifications/styles.css";
import dayjs from "dayjs";
import "dayjs/locale/de";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import App from "./App.tsx";
import { store } from "./store/store.ts";
import "./styles.css";

dayjs.extend(customParseFormat);
const theme = createTheme({
    components: {
        DatePicker: {
            classNames: {
                day: "syTrackdayDatePickerDay",
            },
        },
    },
});

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <MantineProvider theme={theme}>
            <ModalsProvider>
                <DatesProvider settings={{ locale: "de" }}>
                    <Provider store={store}>
                        <Notifications />
                        <App />
                    </Provider>
                </DatesProvider>
            </ModalsProvider>
        </MantineProvider>
    </StrictMode>
);
