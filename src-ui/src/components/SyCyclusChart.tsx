import { LineChart } from "@mantine/charts";
import { ScrollAreaAutosize } from "@mantine/core";
import { useEffect, useState } from "react";
import { useFormContext } from "react-hook-form";
import { useLazyGetCyclusStatisticByIdQuery } from "../store/api/lazyApi";

export default function SyCyclusChart() {
    const { watch } = useFormContext();
    const cyclusId: number | undefined = watch("cyclus");
    const [averageTemperature, setAverageTemperature] = useState<number>();
    const [getCyclusStatisticData, { data: cyclusStatisticData }] =
        useLazyGetCyclusStatisticByIdQuery();

    useEffect(() => {
        if (cyclusId) {
            const fetchData = async (cyclusId: number) => {
                await getCyclusStatisticData({
                    cyclusId,
                });
            };

            void fetchData(cyclusId);
        }
    }, [cyclusId, getCyclusStatisticData]);

    useEffect(() => {
        // Calculate average temperature
        const daysWithTemperature = cyclusStatisticData?.filter(
            (day) => day.temperature
        );
        if (daysWithTemperature?.length === 0) {
            setAverageTemperature(undefined);
            return;
        }
        const sumTemperature = cyclusStatisticData?.reduce(
            (prevValue, currentValue) =>
                prevValue + (currentValue?.temperature || 0),
            0
        );
        if (sumTemperature && daysWithTemperature) {
            setAverageTemperature(sumTemperature / daysWithTemperature.length);
        }
    }, [cyclusStatisticData, setAverageTemperature]);

    return (
        <ScrollAreaAutosize>
            <LineChart
                h="45vh"
                w="90rem"
                data={cyclusStatisticData || []}
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
