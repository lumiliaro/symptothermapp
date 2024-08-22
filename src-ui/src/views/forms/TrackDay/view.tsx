import { SimpleGrid } from "@mantine/core";
import { useFormContext } from "react-hook-form";
import { useSelector } from "react-redux";
import SyBleedingSelect from "../../../components/SyBleedingSelect";
import SyCervicalMucusSelect from "../../../components/SyCervicalMucusSelect";
import SyCervixHeightPositionSelect from "../../../components/SyCervixHeightPositionSelect";
import SyCervixOpeningStateSelect from "../../../components/SyCervixOpeningStateSelect";
import SyCervixTextureSelect from "../../../components/SyCervixTextureSelect";
import SyDisturbanceMultiSelect from "../../../components/SyDisturbanceMultiSelect";
import SyFormButtons from "../../../components/SyFormButtons";
import SySexCheckboxes from "../../../components/SySexCheckboxes";
import SyTemperatureNumberInput from "../../../components/SyTemperatureNumberInput";
import SyTemperatureSlider from "../../../components/SyTemperatureSlider";
import SyTextarea from "../../../components/SyTextarea";
import { DisturbanceEnum, TrackDayDto } from "../../../store/api/generatedApi";
import { RootState } from "../../../store/store";

export default function TrackDayView(props: { formType: "create" | "edit" }) {
    const selectedTrackDate = useSelector(
        (state: RootState) => state.trackDayDate.selectedDateString
    );
    const { reset, watch } = useFormContext<TrackDayDto>();
    const disturbances = watch("disturbances");
    const onReset = () => {
        reset();
    };
    return (
        <SimpleGrid cols={1} mt={10} mb={10}>
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
                formType={props.formType}
                isSubmitDisabled={!selectedTrackDate}
            />
        </SimpleGrid>
    );
}
