import { SelectProps } from "@mantine/core";
import { useGetCervicalMucusOptionsQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyCervicalMucusSelect(props: SelectProps) {
    const { data } = useGetCervicalMucusOptionsQuery(undefined, {
        refetchOnFocus: false,
    });

    return (
        <SySelect
            name="cervicalMucus"
            label="Zervixschleim"
            data={data}
            {...props}
        />
    );
}
