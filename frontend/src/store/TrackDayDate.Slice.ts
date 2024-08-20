import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import dayjs from "dayjs";
import { DATE_FORMAT_BACKEND } from "../utils/DateFormats.utils";

export type TrackDayDateSliceState = {
    selectedDateString?: string;
    selectedMonth?: number;
    selectedYear?: number;
};

const initialState = {
    selectedDateString: dayjs().format(DATE_FORMAT_BACKEND),
    selectedMonth: dayjs().month() + 1,
    selectedYear: dayjs().year(),
} as TrackDayDateSliceState;

const trackDayDateSlice = createSlice({
    name: "trackDayDate",
    initialState,
    reducers: {
        setSelectedTrackDate: (state, action: PayloadAction<string | null>) => {
            if (!action.payload) {
                return;
            }
            state.selectedDateString = action.payload
                ? action.payload
                : undefined;
            state.selectedMonth = action.payload
                ? dayjs(action.payload, DATE_FORMAT_BACKEND).month() + 1
                : undefined;
            state.selectedYear = action.payload
                ? dayjs(action.payload, DATE_FORMAT_BACKEND).year()
                : undefined;
        },
    },
});

export const trackDayDateSliceName = trackDayDateSlice.name;
export const { setSelectedTrackDate } = trackDayDateSlice.actions;
export default trackDayDateSlice.reducer;
