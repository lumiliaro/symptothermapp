import { Box, Checkbox, Fieldset, Group, SimpleGrid } from "@mantine/core";
import { ChangeEvent, useCallback, useEffect, useMemo, useState } from "react";
import { useFormContext } from "react-hook-form";
import { useSelector } from "react-redux";
import SyBleedingSelect from "../../../components/SyBleedingSelect";
import SyCancelButton from "../../../components/SyCancelButton";
import SyCervicalMucusSelect from "../../../components/SyCervicalMucusSelect";
import SyCervixHeightPositionSelect from "../../../components/SyCervixHeightPositionSelect";
import SyCervixOpeningStateSelect from "../../../components/SyCervixOpeningStateSelect";
import SyCervixTextureSelect from "../../../components/SyCervixTextureSelect";
import SyCreateSaveButton from "../../../components/SyCreateSaveButton";
import SyDeleteButton from "../../../components/SyDeleteButton";
import SyDisturbanceMultiSelect from "../../../components/SyDisturbanceMultiSelect";
import SySexCheckboxes from "../../../components/SySexCheckboxes";
import SyTemperatureNumberInput from "../../../components/SyTemperatureNumberInput";
import SyTextarea from "../../../components/SyTextarea";
import { DisturbanceEnum, TrackDay } from "../../../store/api/generatedApi";
import { RootState } from "../../../store/store";
import { isFormClean } from "../../../utils/Form.utils";

export default function TrackDayView(props: {
    formType: "create" | "edit";
    onDelete?: () => void;
}) {
    const { formType, onDelete } = props;
    const { reset, watch, setValue, formState } = useFormContext<TrackDay>();
    const [disturbances, temperature, bleeding] = watch([
        "disturbances",
        "temperature",
        "bleeding",
    ]);
    const selectedTrackDate = useSelector(
        (state: RootState) => state.trackDayDate.selectedDateString
    );
    const [isTemperatureInputDisabled, setIsTemperatureInputDisabled] =
        useState<boolean>(false);

    const onReset = () => {
        reset();
    };

    useEffect(() => {
        if (temperature === undefined || temperature === null) {
            setIsTemperatureInputDisabled(true);
        } else {
            setIsTemperatureInputDisabled(false);
        }
    }, [temperature]);

    useEffect(() => {
        if (!isTemperatureInputDisabled && bleeding) {
            setIsTemperatureInputDisabled(true);
        }
    }, [isTemperatureInputDisabled, bleeding]);

    const onChangeTemperatureDisabledCheckbox = useCallback(
        (event?: ChangeEvent<HTMLInputElement>) => {
            if (event?.currentTarget?.checked) {
                setValue("temperature", undefined, { shouldDirty: true });
            }
            setIsTemperatureInputDisabled(
                event?.currentTarget?.checked ?? false
            );
        },
        [setValue, setIsTemperatureInputDisabled]
    );

    const isOtherDisturbanceNotesDisabled = useMemo(
        () =>
            !disturbances?.find(
                (disturbance) => disturbance === DisturbanceEnum.Other
            ),
        [disturbances]
    );

    return (
        <Box mt="lg">
            <Fieldset
                legend={formType === "create" ? "Erfassen" : "Bearbeiten"}
                disabled={!selectedTrackDate}
            >
                <Group justify="flex-end">
                    <SyCreateSaveButton
                        formType={formType}
                        disabled={isFormClean(formState, formType)}
                    />
                </Group>
                <SimpleGrid cols={1}>
                    <SyTemperatureNumberInput
                        disabled={isTemperatureInputDisabled}
                    />
                    {/* <SyTemperatureSlider  /> */}
                    <Group justify="flex-end">
                        <Checkbox
                            label="Temperatur deaktiviert"
                            checked={isTemperatureInputDisabled}
                            size="md"
                            onChange={onChangeTemperatureDisabledCheckbox}
                        />
                    </Group>
                    <SyBleedingSelect />
                    <SyCervicalMucusSelect />
                    <SyCervixOpeningStateSelect />
                    <SyCervixHeightPositionSelect />
                    <SyCervixTextureSelect />

                    <SySexCheckboxes />
                    <SyDisturbanceMultiSelect />
                    <SyTextarea
                        label="Sonstige Störungen"
                        name="otherDisturbanceNotes"
                        disabled={isOtherDisturbanceNotesDisabled}
                    />
                    <SyTextarea label="Notizen" name="notes" />
                    <Group justify="space-between">
                        <SyCancelButton
                            onReset={onReset}
                            disabled={isFormClean(formState)}
                        />
                        <SyDeleteButton
                            disabled={formType !== "edit"}
                            onConfirm={onDelete || (() => {})}
                        />
                    </Group>
                </SimpleGrid>
            </Fieldset>
        </Box>
    );
}
