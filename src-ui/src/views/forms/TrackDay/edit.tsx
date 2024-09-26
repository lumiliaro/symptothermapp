import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDay,
    TrackDayDto,
    useDeleteTrackDayMutation,
    useUpdateTrackDayMutation,
} from "../../../store/api/generatedApi";
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
    const { callApiWithNotification } = useFormNotification();

    const form = useForm<TrackDayDto>({
        defaultValues: getFormValues(data as TrackDayDto),
        mode: "onChange",
    });

    const onSubmit = async (trackDayDto: TrackDayDto) => {
        if (data.id) {
            await callApiWithNotification({
                apiCall: (notificationId: string) =>
                    update({
                        id: data.id as number,
                        trackDayDto,
                        // @ts-expect-error notificationId is not part of the api call but is needed to update the notification
                        notificationId,
                    }),
                form,
                initNotificationMessage: "Änderungen werden gespeichert...",
                successNotificationMessage:
                    "Änderungen wurden erfolgreich gespeichert.",
            });
        }
    };

    useEffect(() => {
        if (data) {
            form.reset(getFormValues(data as TrackDayDto));
        }
    }, [data, form]);

    const onDelete = async () => {
        if (data.id) {
            await callApiWithNotification({
                apiCall: (notificationId: string) =>
                    // @ts-expect-error notificationId is not part of the api call but is needed to update the notification
                    deleteTrackDay({ id: data.id, notificationId }),
                form,
                initNotificationMessage: "Eintrag wird gelöscht...",
                successNotificationMessage:
                    "Eintrag wurde erfolgreich gelöscht.",
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
