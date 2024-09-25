import { ScrollAreaAutosize } from "@mantine/core";
import dayjs from "dayjs";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import {
    CartesianGrid,
    DotProps,
    Line,
    LineChart,
    ReferenceLine,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";
import {
    CyclusStatisticDto,
    useGetTrackDayMinMaxTemperatureQuery,
} from "../store/api/generatedApi";
import { useLazyGetCyclusStatisticByIdQuery } from "../store/api/lazyApi";
import { RootState } from "../store/store";
import SyCyclusChartDot from "./SyCyclusChartDot";
import SyCyclusChartTooltip from "./SyCyclusChartTooltip";

export default function SyCyclusChart() {
    const [isOpen, setIsOpen] = useState(false);
    const cyclusId = useSelector(
        (state: RootState) => state.cyclus.selectedCyclusId
    );
    const [averageTemperature, setAverageTemperature] = useState<number>();
    const [getCyclusStatisticData, { data: cyclusStatisticData }] =
        useLazyGetCyclusStatisticByIdQuery();
    const { data: minMaxTemperature } = useGetTrackDayMinMaxTemperatureQuery();

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

    if (!cyclusId) {
        return <></>;
    }

    return (
        <ScrollAreaAutosize mt="lg" offsetScrollbars={true}>
            <LineChart
                width={2000}
                height={520}
                data={cyclusStatisticData || []}
            >
                <CartesianGrid strokeDasharray="4 4" />
                <XAxis
                    xAxisId={0}
                    dataKey="date"
                    type="category"
                    orientation="top"
                    interval={0}
                    tickLine={true}
                />
                <XAxis
                    xAxisId={1}
                    dataKey="createdAt"
                    type="category"
                    orientation="top"
                    tickFormatter={(createdAt: string) =>
                        createdAt ? dayjs(createdAt).format("HH:mm") : ""
                    }
                    interval={0}
                    tickLine={true}
                />
                <XAxis
                    xAxisId={2}
                    dataKey="bleeding"
                    interval={0}
                    orientation="bottom"
                    tickFormatter={(value) => (value ? "🩸" : "")}
                    label={{
                        value: "",
                        position: "bottom",
                        offset: 0,
                    }}
                    dy={4}
                    tickLine={true}
                    axisLine={true}
                />
                <XAxis
                    xAxisId={3}
                    dataKey="cervicalMucus"
                    type="category"
                    interval={0}
                    label={{
                        value: "",
                        position: "bottom",
                        offset: 0,
                    }}
                    tickLine={true}
                    axisLine={true}
                />
                <YAxis
                    domain={[
                        minMaxTemperature?.minTemperature || "auto",
                        minMaxTemperature?.maxTemperature || "auto",
                    ]}
                    type="number"
                />
                <ReferenceLine
                    y={averageTemperature}
                    label={{
                        value: `${averageTemperature?.toFixed(2)} °C`,
                        position: "insideLeft",
                        fill: "red",
                        dy: -10,
                    }}
                    stroke="var(--mantine-color-red-8)"
                />
                <Tooltip
                    trigger="click"
                    wrapperStyle={{ pointerEvents: "auto" }}
                    content={
                        <SyCyclusChartTooltip
                            isOpen={isOpen}
                            setIsOpen={setIsOpen}
                        />
                    }
                    isAnimationActive={false}
                />
                <Line
                    type="linear"
                    dataKey="temperature"
                    stroke="var(--mantine-color-indigo-6)"
                    activeDot={{
                        r: 8,
                        onClick: (_event: any, payload: any) => {
                            console.info(payload?.payload);
                            setIsOpen(true);
                        },
                    }}
                    dot={(
                        props: DotProps & { payload: CyclusStatisticDto }
                    ) => (
                        <SyCyclusChartDot
                            key={props.payload.date}
                            cx={props.cx}
                            cy={props.cy}
                            payload={props.payload}
                        />
                    )}
                    isAnimationActive={false}
                />
            </LineChart>
        </ScrollAreaAutosize>
    );
}
