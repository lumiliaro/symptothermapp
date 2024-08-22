import { SegmentedControl } from "@mantine/core";
import { useState } from "react";
import { useGetCervixOpeningStateOptionsQuery } from "../store/api/generatedApi";

export default function SyCervixSegment() {
    const [value, setValue] = useState<string>("");
    const { data } = useGetCervixOpeningStateOptionsQuery();

    if (!data) {
        return <></>;
    }

    return (
        <SegmentedControl
            fullWidth
            value={value}
            onChange={setValue}
            color="blue"
            size="lg"
            data={[{ label: "", value: "" }, ...data]}
        />
    );
}
