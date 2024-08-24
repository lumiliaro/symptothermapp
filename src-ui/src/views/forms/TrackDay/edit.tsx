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

export default function TrackDayEdit(props: { data: TrackDay }) {
    const { data } = props;
    const [update] = useUpdateTrackDayMutation();
    const [deleteTrackDay] = useDeleteTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: data,
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        if (data.id) {
            const id = openLoadingNotification({
                message: "Daten werden gespeichert...",
            });

            await update({ id: data.id, trackDayDto })
                .unwrap()
                .then(() => {
                    updateNotificationToSuccess(id, {
                        message: "Daten wurden erfolgreich gespeichert!",
                    });
                })
                .catch((error) => {
                    updateNotificationToFailure(id, {
                        message: "Daten konnten nicht gespeichert werden!",
                    });
                    handleFormErrors(form, error);
                });
        }
    };

    useEffect(() => {
        if (data) {
            form.reset(data);
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
                    updateNotificationToFailure(id, {
                        message: "Datensatz konnte nicht gelöscht werden!",
                    });
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
