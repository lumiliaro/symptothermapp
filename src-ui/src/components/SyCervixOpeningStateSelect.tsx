import { SelectProps } from "@mantine/core";
import { useGetCervixOpeningStateOptionsQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyCervixOpeningStateSelect(props: SelectProps) {
    const { data } = useGetCervixOpeningStateOptionsQuery(undefined, {
        refetchOnFocus: false,
    });

    return (
        <SySelect
            name="cervixOpeningState"
            label="Öffnung"
            data={data}
            {...props}
        />
    );
}
