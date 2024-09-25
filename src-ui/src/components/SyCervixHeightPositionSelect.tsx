import { SelectProps } from "@mantine/core";
import { useGetCervixHeightPositionOptionsQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyCervixHeightPositionSelect(props: SelectProps) {
    const { data } = useGetCervixHeightPositionOptionsQuery(undefined, {
        refetchOnFocus: false,
    });

    return (
        <SySelect
            name="cervixHeightPosition"
            label="Position"
            data={data}
            {...props}
        />
    );
}
