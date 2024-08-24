import { Center, Container, Tabs } from "@mantine/core";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import SyLineChart from "./components/SyLineChart";
import SyTrackDayDatePicker from "./components/SyTrackDayDatePicker";
import { useLazyGetTrackDayByDateQuery } from "./store/api/lazyApi";
import { RootState } from "./store/store";
import TrackDayCreate from "./views/forms/TrackDay/create";
import TrackDayEdit from "./views/forms/TrackDay/edit";

export default function App() {
    const trackDayDate = useSelector((state: RootState) => state.trackDayDate);
    const [findTrackDayByDate, trackDay] = useLazyGetTrackDayByDateQuery();

    useEffect(() => {
        if (trackDayDate?.selectedDateString) {
            const fetchData = async (selectedDateString: string) => {
                await findTrackDayByDate({ trackDay: selectedDateString });
            };

            void fetchData(trackDayDate?.selectedDateString);
        }
    }, [trackDayDate?.selectedDateString, findTrackDayByDate]);

    return (
        <Container>
            <Center>
                <SyTrackDayDatePicker />
            </Center>
            <Tabs
                defaultValue="track"
                orientation="horizontal"
                keepMounted={false}
            >
                <Tabs.List>
                    <Tabs.Tab value="track">Erfassung</Tabs.Tab>
                    <Tabs.Tab value="statistic">Statistik</Tabs.Tab>
                </Tabs.List>
                <Tabs.Panel value="track">
                    {trackDay.data ? (
                        <TrackDayEdit data={trackDay?.data} />
                    ) : (
                        <TrackDayCreate />
                    )}
                </Tabs.Panel>
                <Tabs.Panel value="statistic">
                    <SyLineChart />
                </Tabs.Panel>
            </Tabs>
        </Container>
    );
}
