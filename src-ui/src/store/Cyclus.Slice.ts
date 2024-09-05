import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type CyclusState = {
    selectedCyclusId: number | null;
};

const initialState = {
    selectedCyclusId: null,
} as CyclusState;

const cyclusSlice = createSlice({
    name: "cyclus",
    initialState,
    reducers: {
        setSelectedCyclusId: (state, action: PayloadAction<number | null>) => {
            state.selectedCyclusId = action.payload;
        },
    },
});

export const cyclusSliceName = cyclusSlice.name;
export const { setSelectedCyclusId } = cyclusSlice.actions;
export default cyclusSlice.reducer;
