import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type TrackDaySliceState = {
    selectedTrackDay?: string;
};

const initialState = {} as TrackDaySliceState;

const trackDaySlice = createSlice({
    name: "trackDay",
    initialState,
    reducers: {
        setSelectedTrackDay: (
            state,
            action: PayloadAction<string | undefined>
        ) => {
            state.selectedTrackDay = action.payload;
        },
    },
});

export const trackDaySliceName = trackDaySlice.name;
export const { setSelectedTrackDay } = trackDaySlice.actions;
export default trackDaySlice.reducer;
