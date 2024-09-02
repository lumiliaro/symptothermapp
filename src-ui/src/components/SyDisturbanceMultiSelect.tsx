import { MultiSelect, MultiSelectProps } from "@mantine/core";
import { useCallback } from "react";
import { useController, useFormContext } from "react-hook-form";
import {
    DisturbanceEnum,
    TrackDayDto,
    useGetDisturbanceOptionsQuery,
} from "../store/api/generatedApi";
import SyInputSkeleton from "./SyInputSkeleton";

export default function SyDisturbanceMultiSelect(props: MultiSelectProps) {
    const { field, fieldState } = useController({
        name: "disturbances",
    });
    const { setValue, watch } = useFormContext<TrackDayDto>();
    const otherDisturbanceNotes = watch("otherDisturbanceNotes");
    const { data } = useGetDisturbanceOptionsQuery();

    const handleChange = useCallback(
        (value?: string[]) => {
            const hasNewValueDisturbanceOtherValueSelected = value?.find(
                (item) => (item as DisturbanceEnum) === DisturbanceEnum.Other
            );

            if (
                otherDisturbanceNotes &&
                !hasNewValueDisturbanceOtherValueSelected
            ) {
                setValue("otherDisturbanceNotes", undefined);
            }

            field.onChange(value);
        },
        [field, setValue, otherDisturbanceNotes]
    );

    if (!data) {
        return <SyInputSkeleton />;
    }

    return (
        <MultiSelect
            {...field}
            value={field.value || []}
            onChange={handleChange}
            label="Störungen"
            placeholder="Bitte auswählen"
            data={data}
            size="md"
            {...props}
            error={fieldState.error?.message}
        />
    );
}
