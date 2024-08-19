import { Checkbox, CheckboxProps, Group } from "@mantine/core";
import { useEffect } from "react";
import { useFormContext } from "react-hook-form";
import { TrackDayDto } from "../store/api/generatedApi";

export default function SySexCheckboxes(props: CheckboxProps) {
    const { register, watch, setValue } = useFormContext<TrackDayDto>();
    const [hadSex, withContraceptives] = watch([
        "hadSex",
        "withContraceptives",
    ]);

    useEffect(() => {
        if (withContraceptives && !hadSex) {
            setValue("withContraceptives", false);
        }
    }, [withContraceptives, hadSex, setValue]);

    return (
        <Group justify="space-between">
            <Checkbox
                label="Sex gehabt"
                size="md"
                {...register("hadSex")}
                {...props}
            />
            <Checkbox
                label="mit Verhütungsmittel"
                size="md"
                {...register("withContraceptives")}
                {...props}
                disabled={!hadSex}
            />
        </Group>
    );
}
