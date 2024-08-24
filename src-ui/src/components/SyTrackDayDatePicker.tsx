import { Indicator } from "@mantine/core";
import { DatePicker, DatePickerProps } from "@mantine/dates";
import dayjs from "dayjs";
import { useCallback, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLazyGetTrackDaysByMonthAndYearQuery } from "../store/api/lazyApi";
import { RootState } from "../store/store";
import { setSelectedTrackDate } from "../store/TrackDayDate.Slice";
import { convertBackendDateStringToDate } from "../utils/Converter.utils";
import { DATE_FORMAT_BACKEND } from "../utils/DateFormats.utils";

export default function SyTrackDayDatePicker() {
    const dispatch = useDispatch();
    const trackDayDate = useSelector((state: RootState) => state.trackDayDate);
    const [getTrackDaysByMonthAndYear, { data: trackDays }] =
        useLazyGetTrackDaysByMonthAndYearQuery();

    useEffect(() => {
        // Get all track days for selected month and year
        if (
            trackDayDate?.selectedMonth !== undefined &&
            trackDayDate?.selectedYear !== undefined
        ) {
            const fetchData = async (month: number, year: number) => {
                await getTrackDaysByMonthAndYear({
                    month,
                    year,
                });
            };

            void fetchData(
                trackDayDate?.selectedMonth,
                trackDayDate?.selectedYear
            );
        }
    }, [trackDayDate, getTrackDaysByMonthAndYear]);

    const selectedDateHandler = (date: Date | null) => {
        const dateString = date
            ? dayjs(date).format(DATE_FORMAT_BACKEND)
            : null;
        dispatch(setSelectedTrackDate(dateString));
    };

    const getDayProps: DatePickerProps["getDayProps"] = useCallback(
        (date: Date) => {
            const dayExists = trackDays?.find(
                (day) =>
                    day.trackDay === dayjs(date).format(DATE_FORMAT_BACKEND)
            );
            if (dayExists) {
                return {
                    style: {
                        borderBottom: "3px solid var(--mantine-color-indigo-6)",
                    },
                };
            }

            return {};
        },
        [trackDays]
    );

    const dayRenderer: DatePickerProps["renderDay"] = useCallback(
        (date: Date) => {
            const dayExistsWithBleeding = trackDays?.find(
                (day) =>
                    day.trackDay === dayjs(date).format(DATE_FORMAT_BACKEND) &&
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
        },
        [trackDays]
    );

    return (
        <DatePicker
            allowDeselect
            firstDayOfWeek={1}
            size="xl"
            value={convertBackendDateStringToDate(
                trackDayDate.selectedDateString
            )}
            onChange={selectedDateHandler}
            onMonthSelect={selectedDateHandler}
            onNextMonth={selectedDateHandler}
            onPreviousMonth={selectedDateHandler}
            onYearSelect={selectedDateHandler}
            onNextYear={selectedDateHandler}
            onPreviousYear={selectedDateHandler}
            getDayProps={getDayProps}
            hideOutsideDates
            maxDate={new Date()}
            renderDay={dayRenderer}
        />
    );
}
