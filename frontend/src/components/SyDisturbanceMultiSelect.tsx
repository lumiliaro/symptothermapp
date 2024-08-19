import { MultiSelect, MultiSelectProps } from "@mantine/core";
import { useController } from "react-hook-form";
import { useGetDisturbanceOptionsQuery } from "../store/api/generatedApi";

export default function SyDisturbanceMultiSelect(props: MultiSelectProps) {
    const { field } = useController({
        name: "disturbances",
    });
    const { data } = useGetDisturbanceOptionsQuery();

    return (
        <MultiSelect
            {...field}
            value={field.value || []}
            label="Störungen"
            placeholder="Bitte auswählen"
            data={data}
            size="md"
            {...props}
        />
    );
}
