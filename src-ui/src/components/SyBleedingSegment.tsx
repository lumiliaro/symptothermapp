import { SegmentedControl } from "@mantine/core";
import { useState } from "react";
import {
    BleedingEnum,
    useGetBleedingOptionsQuery,
} from "../store/api/generatedApi";

export default function SyBleedingSegment() {
    const [value, setValue] = useState<string>(BleedingEnum.Stark);
    const { data } = useGetBleedingOptionsQuery();

    if (!data) {
        return;
    }

    return (
        <SegmentedControl
            fullWidth
            value={value}
            onChange={setValue}
            color="blue"
            size="lg"
            data={data}
        />
    );
}
