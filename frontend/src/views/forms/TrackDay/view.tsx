import { SimpleGrid } from "@mantine/core";
import { useFormContext } from "react-hook-form";
import SyBleedingSelect from "../../../components/SyBleedingSelect";
import SyCervicalMucusSelect from "../../../components/SyCervicalMucusSelect";
import SyCervixHeightPositionSelect from "../../../components/SyCervixHeightPositionSelect";
import SyCervixOpeningStateSelect from "../../../components/SyCervixOpeningStateSelect";
import SyCervixTextureSelect from "../../../components/SyCervixTextureSelect";
import SyDisturbanceMultiSelect from "../../../components/SyDisturbanceMultiSelect";
import SyFormButtons from "../../../components/SyFormButtons";
import SyLineChart from "../../../components/SyLineChart";
import SySexCheckboxes from "../../../components/SySexCheckboxes";
import SyTemperatureNumberInput from "../../../components/SyTemperatureNumberInput";
import SyTemperatureSlider from "../../../components/SyTemperatureSlider";
import SyTextarea from "../../../components/SyTextarea";
import { DisturbanceEnum, TrackDayDto } from "../../../store/api/generatedApi";

export default function TrackDayView(props: {
    formType: "create" | "edit";
    selectedTrackDay?: string;
}) {
    const { reset, watch } = useFormContext<TrackDayDto>();
    const disturbances = watch("disturbances");
    const onReset = () => {
        reset();
    };
    return (
        <SimpleGrid cols={1}>
            <SyLineChart />

            <SyTemperatureNumberInput disabled={!props.selectedTrackDay} />
            <SyTemperatureSlider disabled={!props.selectedTrackDay} />
            <SyBleedingSelect disabled={!props.selectedTrackDay} />
            <SyCervicalMucusSelect disabled={!props.selectedTrackDay} />
            {/* <Fieldset legend="Gebärmutterhals"> */}
            <SyCervixOpeningStateSelect disabled={!props.selectedTrackDay} />
            <SyCervixHeightPositionSelect disabled={!props.selectedTrackDay} />
            <SyCervixTextureSelect disabled={!props.selectedTrackDay} />
            {/* </Fieldset> */}
            <SySexCheckboxes disabled={!props.selectedTrackDay} />
            <SyDisturbanceMultiSelect disabled={!props.selectedTrackDay} />
            {disturbances?.find(
                (disturbance) => disturbance === DisturbanceEnum.Sonstiges
            ) && (
                <SyTextarea
                    label="Sonstige Störungen"
                    name="otherDisturbanceNotes"
                    disabled={!props.selectedTrackDay}
                />
            )}
            <SyTextarea
                label="Notizen"
                name="notes"
                disabled={!props.selectedTrackDay}
            />
            <SyFormButtons
                onReset={onReset}
                formType={props.formType}
                isSubmitDisabled={!props.selectedTrackDay}
            />
        </SimpleGrid>
    );
}
