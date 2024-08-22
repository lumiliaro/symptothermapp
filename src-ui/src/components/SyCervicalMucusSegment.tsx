import { SegmentedControl } from "@mantine/core";
import { useState } from "react";
import {
    CervicalMucusEnum,
    useGetCervicalMucusOptionsQuery,
} from "../store/api/generatedApi";

export default function SyCervicalMucusSegment() {
    const [value, setValue] = useState<string>(CervicalMucusEnum.Trocken);
    const { data } = useGetCervicalMucusOptionsQuery();

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
