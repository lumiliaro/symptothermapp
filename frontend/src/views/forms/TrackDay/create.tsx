import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDayDto,
    useStoreTrackDayMutation,
} from "../../../store/api/generatedApi";
import TrackDayView from "./view";

export default function TrackDayCreate(props: { selectedTrackDay?: string }) {
    const [store] = useStoreTrackDayMutation();
    const {
        openLoadingNotification,
        updateNotificationToSuccess,
        updateNotificationToFailure,
    } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: {
            trackDay: props.selectedTrackDay,
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
            .catch(() => {
                updateNotificationToFailure(id, {
                    message: "Daten konnten nicht erstellt werden!",
                });
            });
    };

    const onReset = () => {
        form.reset();
    };

    useEffect(() => {
        if (props.selectedTrackDay) {
            form.setValue("trackDay", props.selectedTrackDay);
        }
    }, [props.selectedTrackDay, form]);

    return (
        <FormProvider {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} onReset={onReset}>
                <TrackDayView
                    selectedTrackDay={props.selectedTrackDay}
                    formType="create"
                />
            </form>
        </FormProvider>
    );
}
