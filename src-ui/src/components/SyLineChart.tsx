import { LineChart } from "@mantine/charts";
import { ScrollAreaAutosize } from "@mantine/core";
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
            const fetchData = async (month: number, year: number) => {
                await getStatisticData({
                    month,
                    year,
                });
            };

            void fetchData(
                trackDayDate?.selectedMonth,
                trackDayDate?.selectedYear
            );
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
        <ScrollAreaAutosize>
            <LineChart
                h={"45vh"}
                w="90rem"
                data={statisticData || []}
                p="lg"
                dataKey="date"
                series={[{ name: "temperature", color: "indigo.6" }]}
                curveType="linear"
                tickLine="xy"
                gridAxis="xy"
                connectNulls={false}
                yAxisProps={{ domain: ["auto", "auto"] }}
                valueFormatter={(temperature) => `${temperature.toFixed(2)} °C`}
                tooltipAnimationDuration={200}
                referenceLines={[
                    {
                        y: averageTemperature,
                        label: `Durchschnitt ${averageTemperature?.toFixed(
                            2
                        )} °C`,
                        color: "red.6",
                    },
                ]}
            />
        </ScrollAreaAutosize>
    );
}
