import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDay,
    TrackDayDto,
    useDeleteTrackDayMutation,
    useUpdateTrackDayMutation,
} from "../../../store/api/generatedApi";
import handleFormErrors from "../../../utils/FormErrorHandler.utils";
import TrackDayView from "./view";

function getFormValues(data: TrackDayDto) {
    return {
        temperature: data.temperature,
        day: data.day,
        bleeding: data.bleeding,
        cervicalMucus: data.cervicalMucus,
        cervixOpeningState: data.cervixOpeningState,
        cervixHeightPosition: data.cervixHeightPosition,
        cervixTexture: data.cervixTexture,
        hadSex: data.hadSex || false,
        withContraceptives: data.withContraceptives || false,
        disturbances: data.disturbances || [],
        otherDisturbanceNotes: data.otherDisturbanceNotes || "",
        notes: data.notes || "",
    };
}
export default function TrackDayEdit(props: { data: TrackDay }) {
    const { data } = props;
    const [update] = useUpdateTrackDayMutation();
    const [deleteTrackDay] = useDeleteTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        // updateNotificationToFailure,
    } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: getFormValues(data),
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        if (data.id) {
            const notificationId = openLoadingNotification({
                message: "Daten werden gespeichert...",
            });

            await update({
                id: data.id,
                trackDayDto,
                // @ts-expect-error notificationId is not part of the api call but is needed to update the notification
                notificationId,
            })
                .unwrap()
                .then(() => {
                    updateNotificationToSuccess(notificationId, {
                        message: "Daten wurden erfolgreich gespeichert!",
                    });
                })
                .catch((error) => {
                    handleFormErrors(form, error);
                });
        }
    };

    useEffect(() => {
        if (data) {
            form.reset(getFormValues(data));
        }
    }, [data, form]);

    const onDelete = async () => {
        if (data.id) {
            const id = openLoadingNotification({
                message: "Datensatz wird gelöscht...",
            });

            await deleteTrackDay({ id: data.id })
                .unwrap()
                .then(() => {
                    updateNotificationToSuccess(id, {
                        message: "Datensatz wurden erfolgreich gelöscht!",
                    });
                })
                .catch((error) => {
                    handleFormErrors(form, error);
                });
        }
    };

    return (
        <FormProvider {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
                <TrackDayView formType="edit" onDelete={onDelete} />
            </form>
        </FormProvider>
    );
}
