import { Box, Group, SimpleGrid } from "@mantine/core";
import { useFormContext } from "react-hook-form";
import { useSelector } from "react-redux";
import SyBleedingSelect from "../../../components/SyBleedingSelect";
import SyCervicalMucusSelect from "../../../components/SyCervicalMucusSelect";
import SyCervixHeightPositionSelect from "../../../components/SyCervixHeightPositionSelect";
import SyCervixOpeningStateSelect from "../../../components/SyCervixOpeningStateSelect";
import SyCervixTextureSelect from "../../../components/SyCervixTextureSelect";
import SyDeleteButton from "../../../components/SyDeleteButton";
import SyDisturbanceMultiSelect from "../../../components/SyDisturbanceMultiSelect";
import SyFormButtons from "../../../components/SyFormButtons";
import SySexCheckboxes from "../../../components/SySexCheckboxes";
import SyTemperatureNumberInput from "../../../components/SyTemperatureNumberInput";
import SyTemperatureSlider from "../../../components/SyTemperatureSlider";
import SyTextarea from "../../../components/SyTextarea";
import { DisturbanceEnum, TrackDay } from "../../../store/api/generatedApi";
import { RootState } from "../../../store/store";

export default function TrackDayView(props: {
    formType: "create" | "edit";
    onDelete?: () => void;
}) {
    const { formType, onDelete } = props;
    const { reset, watch } = useFormContext<TrackDay>();
    const disturbances = watch("disturbances");
    const selectedTrackDate = useSelector(
        (state: RootState) => state.trackDayDate.selectedDateString
    );
    const onReset = () => {
        reset();
    };

    return (
        <Box mt="sm" mb="lg">
            <Group justify="flex-end">
                <SyDeleteButton
                    disabled={formType !== "edit"}
                    onConfirm={onDelete || (() => {})}
                />
            </Group>
            <SimpleGrid cols={1}>
                <SyTemperatureNumberInput disabled={!selectedTrackDate} />
                <SyTemperatureSlider disabled={!selectedTrackDate} />
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
                    (disturbance) => disturbance === DisturbanceEnum.Sonstiges
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
                <SyFormButtons
                    onReset={onReset}
                    formType={formType}
                    isSubmitDisabled={!selectedTrackDate}
                />
            </SimpleGrid>
        </Box>
    );
}
