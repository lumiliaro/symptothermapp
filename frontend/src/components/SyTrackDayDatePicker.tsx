import { DatePicker, DatePickerProps } from "@mantine/dates";
import dayjs from "dayjs";
import { useCallback, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useGetTrackDaysByMonthQuery } from "../store/api/generatedApi";
import { setSelectedTrackDay } from "../store/TrackDay.Slice";

export default function SyTrackDayDatePicker() {
    const dispatch = useDispatch();
    const [trackDay, setTrackDay] = useState<Date | null>(null);
    const [selectedMonth, setSelectedMonth] = useState<Date | null>(null);
    const [month, setMonth] = useState<number>(dayjs().month());

    const { data: trackDays } = useGetTrackDaysByMonthQuery({
        month,
    });

    useEffect(() => {
        let month = dayjs().month();
        if (selectedMonth) {
            month = dayjs(selectedMonth).month();
        }
        setMonth(month);
    }, [selectedMonth]);

    useEffect(() => {
        dispatch(
            setSelectedTrackDay(
                trackDay ? dayjs(trackDay).format("YYYY-MM-DD") : undefined
            )
        );
    }, [trackDay, dispatch]);

    const getDayProps: DatePickerProps["getDayProps"] = useCallback(
        (date: Date) => {
            const dayExists = trackDays?.find(
                (day) => day.trackDay === dayjs(date).format("YYYY-MM-DD")
            );
            if (dayExists) {
                return {
                    style: {
                        backgroundColor: "var(--mantine-color-red-filled)",
                        color: "var(--mantine-color-white)",
                    },
                };
            }

            return {};
        },
        [trackDays]
    );

    return (
        <DatePicker
            allowDeselect
            firstDayOfWeek={1}
            size="xl"
            value={trackDay}
            onChange={setTrackDay}
            onMonthSelect={setSelectedMonth}
            onNextMonth={setSelectedMonth}
            onPreviousMonth={setSelectedMonth}
            getDayProps={getDayProps}
            hideOutsideDates
            maxDate={new Date()}
        />
    );
}
