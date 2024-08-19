import { Center, Container } from "@mantine/core";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import SyTrackDayDatePicker from "./components/SyTrackDayDatePicker";
import { useLazyGetTrackDayByDateQuery } from "./store/api/lazyApi";
import { RootState } from "./store/store";
import TrackDayCreate from "./views/forms/TrackDay/create";
import TrackDayEdit from "./views/forms/TrackDay/edit";

function App() {
    const selectedTrackDay = useSelector(
        (state: RootState) => state.trackDay.selectedTrackDay
    );

    const [findTrackDayByDate, trackDay] = useLazyGetTrackDayByDateQuery();

    useEffect(() => {
        if (selectedTrackDay) {
            findTrackDayByDate({ trackDay: selectedTrackDay });
        }
    }, [selectedTrackDay, findTrackDayByDate]);

    return (
        <Container>
            <Center>
                <SyTrackDayDatePicker />
            </Center>
            {trackDay.data ? (
                <TrackDayEdit data={trackDay?.data} />
            ) : (
                <TrackDayCreate selectedTrackDay={selectedTrackDay} />
            )}
        </Container>
    );
}

export default App;
