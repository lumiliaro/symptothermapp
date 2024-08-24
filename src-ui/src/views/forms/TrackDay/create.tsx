import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useSelector } from "react-redux";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDayDto,
    useStoreTrackDayMutation,
} from "../../../store/api/generatedApi";
import { RootState } from "../../../store/store";
import handleFormErrors from "../../../utils/FormErrorHandler.utils";
import TrackDayView from "./view";

export default function TrackDayCreate() {
    const selectedTrackDate = useSelector(
        (state: RootState) => state.trackDayDate.selectedDateString
    );
    const [store] = useStoreTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: {
            trackDay: selectedTrackDate,
            temperature: 36.2,
        },
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        const id = openLoadingNotification({
            message: "Daten werden erstellt...",
        });

        await store({ trackDayDto })
            .unwrap()
            .then(() => {
                updateNotificationToSuccess(id, {
                    message: "Daten wurden erstellt!",
                });
            })
            .catch((error) => {
                updateNotificationToFailure(id, {
                    message: "Daten konnten nicht erstellt werden!",
                });
                handleFormErrors(form, error);
            });
    };

    const onReset = () => {
        form.reset();
    };

    useEffect(() => {
        if (selectedTrackDate) {
            form.setValue("trackDay", selectedTrackDate);
        }
    }, [selectedTrackDate, form]);

    return (
        <FormProvider {...form}>
            <form onSubmit={void form.handleSubmit(onSubmit)} onReset={onReset}>
                <TrackDayView formType="create" />
            </form>
        </FormProvider>
    );
}
