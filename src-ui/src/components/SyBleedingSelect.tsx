import { SelectProps } from "@mantine/core";
import { useGetBleedingOptionsQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyBleedingSelect(props: SelectProps) {
    const { data } = useGetBleedingOptionsQuery(undefined, {
        refetchOnFocus: false,
    });

    return <SySelect name="bleeding" label="Blutung" data={data} {...props} />;
}
