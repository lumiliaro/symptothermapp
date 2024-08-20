import dayjs from "dayjs";
import { DATE_FORMAT_BACKEND } from "./DateFormats.utils";

export const convertBackendDateStringToDate = (dateString?: string) => {
    return dateString ? dayjs(dateString, DATE_FORMAT_BACKEND).toDate() : null;
};
