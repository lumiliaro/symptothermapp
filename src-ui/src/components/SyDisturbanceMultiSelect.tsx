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
    const { field, fieldState } = useController<TrackDayDto, "disturbances">({
        name: "disturbances",
    });
    const { setValue, getValues } = useFormContext<TrackDayDto>();
    const { data } = useGetDisturbanceOptionsQuery(undefined, {
        refetchOnFocus: false,
    });

    const handleChange = useCallback(
        (value?: string[]) => {
            const hasNewValueDisturbanceOtherValueSelected = value?.find(
                (item) => (item as DisturbanceEnum) === DisturbanceEnum.Other
            );

            if (
                getValues("otherDisturbanceNotes") &&
                !hasNewValueDisturbanceOtherValueSelected
            ) {
                setValue("otherDisturbanceNotes", undefined);
            }

            field.onChange(value);
        },
        [field, setValue, getValues]
    );

    if (!data) {
        return <SyInputSkeleton />;
    }

    return (
        <MultiSelect
            {...field}
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
