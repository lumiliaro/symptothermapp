import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useSelector } from "react-redux";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDayDto,
    useCreateTrackDayMutation,
} from "../../../store/api/generatedApi";
import { RootState } from "../../../store/store";
import handleFormErrors from "../../../utils/FormErrorHandler.utils";
import TrackDayView from "./view";

export default function TrackDayCreate() {
    const selectedTrackDate = useSelector(
        (state: RootState) => state.trackDayDate.selectedDateString
    );
    const [store] = useCreateTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        // updateNotificationToFailure,
    } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: {
            day: selectedTrackDate,
            temperature: 36.2,
            bleeding: null,
            cervicalMucus: null,
            cervixOpeningState: null,
            cervixHeightPosition: null,
            cervixTexture: null,
            hadSex: false,
            withContraceptives: false,
            disturbances: [],
            otherDisturbanceNotes: "",
            notes: "",
        },
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        const notificationId = openLoadingNotification({
            message: "Daten werden erstellt...",
        });

        // @ts-expect-error notificationId is not part of the api call but is needed to update the notification
        await store({ trackDayDto, notificationId })
            .unwrap()
            .then(() => {
                updateNotificationToSuccess(notificationId, {
                    message: "Daten wurden erstellt!",
                });
            })
            .catch((error) => {
                handleFormErrors(form, error);
            });
    };

    const onReset = () => {
        form.reset();
    };

    useEffect(() => {
        if (selectedTrackDate) {
            form.setValue("day", selectedTrackDate);
        }
    }, [selectedTrackDate, form]);

    return (
        <FormProvider {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} onReset={onReset}>
                <TrackDayView formType="create" />
            </form>
        </FormProvider>
    );
}
