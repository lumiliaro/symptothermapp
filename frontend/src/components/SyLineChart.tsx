import { LineChart } from "@mantine/charts";
import { useGetTrackDaysStatisticByMonthAndYearQuery } from "../store/api/generatedApi";

export default function SyLineChart() {
    const { data } = useGetTrackDaysStatisticByMonthAndYearQuery({
        month: 7,
        year: 2024,
    });

    return (
        <LineChart
            h={300}
            data={data || []}
            dataKey="date"
            series={[{ name: "temperature", color: "indigo.6" }]}
            curveType="linear"
            tickLine="xy"
            gridAxis="xy"
            connectNulls={false}
            yAxisProps={{ domain: ["auto", "auto"] }}
        />
    );
}
