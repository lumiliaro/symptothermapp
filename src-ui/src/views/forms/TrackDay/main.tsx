import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useLazyGetTrackDayByDateQuery } from "../../../store/api/lazyApi";
import { RootState } from "../../../store/store";
import TrackDayCreate from "./create";
import TrackDayEdit from "./edit";

export default function TrackDayMainView() {
    const trackDayDate = useSelector((state: RootState) => state.trackDayDate);
    const [findTrackDayByDate, trackDay] = useLazyGetTrackDayByDateQuery();

    useEffect(() => {
        if (trackDayDate?.selectedDateString) {
            const fetchData = async (selectedDateString: string) => {
                await findTrackDayByDate({ day: selectedDateString });
            };

            void fetchData(trackDayDate?.selectedDateString);
        }
    }, [trackDayDate?.selectedDateString, findTrackDayByDate]);

    if (trackDay.isError) {
        return <>Fehler beim Laden der Daten!</>;
    }

    return (
        <>
            {trackDay.data ? (
                <TrackDayEdit data={trackDay?.data} />
            ) : (
                <TrackDayCreate
                    selectedTrackDate={trackDayDate?.selectedDateString}
                />
            )}
        </>
    );
}
