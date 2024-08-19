import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDay,
    TrackDayDto,
    useUpdateTrackDayMutation,
} from "../../../store/api/generatedApi";
import TrackDayView from "./view";

export default function TrackDayEdit(props: { data: TrackDay }) {
    const [update] = useUpdateTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    } = useFormNotification();
    const form = useForm<TrackDayDto>({
        defaultValues: props.data,
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        if (props.data.id) {
            const id = openLoadingNotification({
                message: "Daten werden gespeichert...",
            });

            await update({ id: props.data.id, trackDayDto })
                .unwrap()
                .then(() => {
                    updateNotificationToSuccess(id, {
                        message: "Daten wurden gespeichert!",
                    });
                })
                .catch(() => {
                    updateNotificationToFailure(id, {
                        message: "Daten konnten nicht gespeichert werden!",
                    });
                });
        }
    };

    useEffect(() => {
        if (props.data) {
            form.reset(props.data);
        }
    }, [props.data, form]);

    return (
        <FormProvider {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
                <TrackDayView
                    selectedTrackDay={props.data.trackDay}
                    formType="edit"
                />
            </form>
        </FormProvider>
    );
}
