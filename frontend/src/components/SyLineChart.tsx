import { LineChart } from "@mantine/charts";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useLazyGetTrackDaysStatisticByMonthAndYearQuery } from "../store/api/lazyApi";
import { RootState } from "../store/store";

export default function SyLineChart() {
    const trackDayDate = useSelector((state: RootState) => state.trackDayDate);
    const [averageTemperature, setAverageTemperature] = useState<number>();
    const [getStatisticData, { data: statisticData }] =
        useLazyGetTrackDaysStatisticByMonthAndYearQuery();

    useEffect(() => {
        if (trackDayDate?.selectedMonth && trackDayDate?.selectedYear) {
            getStatisticData({
                month: trackDayDate?.selectedMonth,
                year: trackDayDate?.selectedYear,
            });
        }
    }, [trackDayDate, getStatisticData]);

    useEffect(() => {
        // Calculate average temperature
        const daysWithTemperature = statisticData?.filter(
            (day) => day.temperature
        );
        if (daysWithTemperature?.length === 0) {
            setAverageTemperature(undefined);
            return;
        }
        const sumTemperature = statisticData?.reduce(
            (prevValue, currentValue) =>
                prevValue + (currentValue?.temperature || 0),
            0
        );
        if (sumTemperature && daysWithTemperature) {
            setAverageTemperature(sumTemperature / daysWithTemperature.length);
        }
    }, [statisticData, setAverageTemperature]);

    return (
        <LineChart
            h={"45vh"}
            data={statisticData || []}
            mt={15}
            dataKey="date"
            series={[{ name: "temperature", color: "indigo.6" }]}
            curveType="linear"
            tickLine="xy"
            gridAxis="xy"
            connectNulls={false}
            yAxisProps={{ domain: [33, 42] }}
            valueFormatter={(temperature) => `${temperature.toFixed(2)} °C`}
            tooltipAnimationDuration={200}
            referenceLines={[
                {
                    y: averageTemperature,
                    label: `Durchschnitt ${averageTemperature?.toFixed(2)} °C`,
                    color: "red.6",
                },
            ]}
        />
    );
}
