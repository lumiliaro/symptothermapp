import { Dot, DotProps } from "recharts";
import { CyclusStatisticDto } from "../store/api/generatedApi";

export default function SyCyclusChartDot(
    props: DotProps & { payload: CyclusStatisticDto }
) {
    const { cx, cy, payload } = props;
    let fill = "var(--mantine-color-indigo-6)";

    switch (payload.cyclusDotType) {
        case "BLEEDING":
            fill = "var(--mantine-color-red-6)";
            break;
        case "FERTILE":
            fill = "var(--mantine-color-yellow-6)";
            break;
        case "INFERTILE":
            fill = "var(--mantine-color-green-6)";
            break;
    }

    return <Dot cx={cx} cy={cy} r={5} fill={fill} />;
}
