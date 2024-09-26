import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import useFormNotification from "../../../hooks/useFormNotification";
import {
    TrackDayDto,
    useCreateTrackDayMutation,
} from "../../../store/api/generatedApi";
import TrackDayView from "./view";

export default function TrackDayCreate(props: { selectedTrackDate?: string }) {
    const { selectedTrackDate } = props;
    const [store] = useCreateTrackDayMutation();
    const { callApiWithNotification } = useFormNotification();

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
        await callApiWithNotification({
            apiCall: (notificationId: string) =>
                // @ts-expect-error notificationId is not part of the api call but is needed to update the notification
                store({ trackDayDto, notificationId }),
            form,
            initNotificationMessage: "Neuer Eintrag wird erstellt...",
            successNotificationMessage:
                "Neuer Eintrag wurde erfolgreich erstellt.",
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
