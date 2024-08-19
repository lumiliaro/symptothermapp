import { Indicator } from "@mantine/core";
import { DatePicker, DatePickerProps } from "@mantine/dates";
import dayjs from "dayjs";
import { useCallback, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { setSelectedTrackDay } from "../store/TrackDay.Slice";
import { useGetTrackDaysByMonthAndYearQuery } from "../store/api/generatedApi";

export default function SyTrackDayDatePicker() {
    const dispatch = useDispatch();
    const [trackDay, setTrackDay] = useState<Date | null>(null);
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [month, setMonth] = useState<number>(dayjs().month());
    const [year, setYear] = useState<number>(dayjs().year());

    const { data: trackDays } = useGetTrackDaysByMonthAndYearQuery({
        month,
        year,
    });

    useEffect(() => {
        let currentDate = dayjs();

        if (selectedDate) {
            currentDate = dayjs(selectedDate);
        }
        setMonth(currentDate.month());
        setYear(currentDate.year());
    }, [selectedDate]);

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
                        // backgroundColor: "var(--mantine-color-red-filled)",
                        // color: "var(--mantine-color-white)",
                        borderBottom:
                            "3px solid var(--mantine-color-indigo-filled)",
                    },
                };
            }

            return {};
        },
        [trackDays]
    );

    const dayRenderer: DatePickerProps["renderDay"] = (date) => {
        const dayExistsWithBleeding = trackDays?.find(
            (day) =>
                day.trackDay === dayjs(date).format("YYYY-MM-DD") &&
                !!day.bleeding
        );
        if (dayExistsWithBleeding) {
            const day = date.getDate();
            return (
                <Indicator size={8} color="red" offset={-5}>
                    <div>{day}</div>
                </Indicator>
            );
        }
    };

    return (
        <DatePicker
            allowDeselect
            firstDayOfWeek={1}
            size="xl"
            value={trackDay}
            onChange={setTrackDay}
            onMonthSelect={setSelectedDate}
            onNextMonth={setSelectedDate}
            onPreviousMonth={setSelectedDate}
            getDayProps={getDayProps}
            hideOutsideDates
            maxDate={new Date()}
            renderDay={dayRenderer}
        />
    );
}
