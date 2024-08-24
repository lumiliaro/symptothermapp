import { MultiSelect, MultiSelectProps } from "@mantine/core";
import { useController } from "react-hook-form";
import { useGetDisturbanceOptionsQuery } from "../store/api/generatedApi";

export default function SyDisturbanceMultiSelect(props: MultiSelectProps) {
    const { field, fieldState } = useController({
        name: "disturbances",
    });
    const { data } = useGetDisturbanceOptionsQuery();

    if (!data) {
        return <></>;
    }

    return (
        <MultiSelect
            {...field}
            value={field.value || []}
            label="Störungen"
            placeholder="Bitte auswählen"
            data={data}
            size="md"
            {...props}
            error={fieldState.error?.message}
        />
    );
}
