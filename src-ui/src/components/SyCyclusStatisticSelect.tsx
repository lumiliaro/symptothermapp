import { SelectProps } from "@mantine/core";
import dayjs from "dayjs";
import { useGetAllCycliQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyCyclusStatisticSelect(props: SelectProps) {
    const { data: cycli } = useGetAllCycliQuery();
    const data =
        cycli?.map((cyclus) => ({
            label: dayjs(cyclus.date, "YYYY-MM-DD").format("DD.MM.YYYY"),
            value: cyclus.id?.toString() || "",
        })) || [];

    return <SySelect name="cyclus" label="Zyklus" data={data} {...props} />;
}
