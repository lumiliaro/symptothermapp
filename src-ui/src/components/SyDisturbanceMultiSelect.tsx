import { MultiSelect, MultiSelectProps } from "@mantine/core";
import { useController } from "react-hook-form";
import { useGetDisturbanceOptionsQuery } from "../store/api/generatedApi";
import SyInputSkeleton from "./SyInputSkeleton";

export default function SyDisturbanceMultiSelect(props: MultiSelectProps) {
    const { field, fieldState } = useController({
        name: "disturbances",
    });
    const { data } = useGetDisturbanceOptionsQuery();

    if (!data) {
        return <SyInputSkeleton />;
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
