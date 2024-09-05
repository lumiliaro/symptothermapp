import { Box, Checkbox, Group, SimpleGrid } from "@mantine/core";
import { useEffect, useState } from "react";
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

export default function TrackDayView(props: {
    formType: "create" | "edit";
    onDelete?: () => void;
}) {
    const { formType, onDelete } = props;
    const { reset, watch, setValue } = useFormContext<TrackDay>();
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

    return (
        <Box mt="lg">
            <Group justify="flex-end">
                <SyCreateSaveButton
                    formType={formType}
                    isSubmitDisabled={!selectedTrackDate}
                />
            </Group>
            <SimpleGrid cols={1}>
                <SyTemperatureNumberInput
                    disabled={!selectedTrackDate || isTemperatureInputDisabled}
                />
                {/* <SyTemperatureSlider disabled={!selectedTrackDate} /> */}
                <Group justify="flex-end">
                    <Checkbox
                        label="Temperatur deaktiviert"
                        checked={isTemperatureInputDisabled}
                        size="md"
                        onChange={(event) => {
                            if (event.currentTarget.checked) {
                                setValue("temperature", undefined);
                            }
                            setIsTemperatureInputDisabled(
                                event.currentTarget.checked
                            );
                        }}
                    />
                </Group>
                <SyBleedingSelect disabled={!selectedTrackDate} />
                <SyCervicalMucusSelect disabled={!selectedTrackDate} />
                {/* <Fieldset legend="Gebärmutterhals"> */}
                <SyCervixOpeningStateSelect disabled={!selectedTrackDate} />
                <SyCervixHeightPositionSelect disabled={!selectedTrackDate} />
                <SyCervixTextureSelect disabled={!selectedTrackDate} />
                {/* </Fieldset> */}
                <SySexCheckboxes disabled={!selectedTrackDate} />
                <SyDisturbanceMultiSelect disabled={!selectedTrackDate} />
                {disturbances?.find(
                    (disturbance) => disturbance === DisturbanceEnum.Other
                ) && (
                    <SyTextarea
                        label="Sonstige Störungen"
                        name="otherDisturbanceNotes"
                        disabled={!selectedTrackDate}
                    />
                )}
                <SyTextarea
                    label="Notizen"
                    name="notes"
                    disabled={!selectedTrackDate}
                />
                <Group justify="space-between">
                    <SyCancelButton onReset={onReset} />
                    <SyDeleteButton
                        disabled={formType !== "edit"}
                        onConfirm={onDelete || (() => {})}
                    />
                </Group>
            </SimpleGrid>
        </Box>
    );
}
